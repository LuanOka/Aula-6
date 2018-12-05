package br.usjt.ads.arqdes.model.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.javabeans.Creditos;
import br.usjt.ads.arqdes.model.javabeans.Equipe;
import br.usjt.ads.arqdes.model.javabeans.Lancamentos;
import br.usjt.ads.arqdes.model.javabeans.Movie;
import br.usjt.ads.arqdes.model.javabeans.Populares;
@Service
public class FilmeService {
	private static final String API_KEY = "58a9371249f0e265b1a1ca19711d9b88";
	public static final String BASE_URL = "https://api.themoviedb.org/3/movie"; 
	public static final String POPULAR = "/popular?api_key="+API_KEY+"&language=pt-BR&page=1&region=BR";
	public static final String UPCOMING = "/upcoming?api_key="+API_KEY+"&language=pt-BR&page=1&region=BR";
	public static final String POSTER_URL = "https://image.tmdb.org/t/p/w400";
	public static final String CREDITS = "/credits?api_key="+API_KEY;
	
	@Autowired
	private FilmeDAO dao;
	
	public Filme buscarFilme(int id) throws IOException{
		return dao.buscarFilme(id);
	}
	
	@Transactional
	public Filme inserirFilme(Filme filme) throws IOException {
		int id = dao.inserirFilme(filme);
		filme.setId(id);
		return filme;
	}
	
	@Transactional
	public void atualizarFilme(Filme filme) throws IOException {
		dao.atualizarFilme(filme);
	}
	
	@Transactional
	public void excluirFilme(int id) throws IOException {
		dao.excluirFilme(id);
	}

	public List<Filme> listarFilmes(String chave) throws IOException{
		return dao.listarFilmes(chave);
	}

	public List<Filme> listarFilmes() throws IOException{
		return dao.listarFilmes();
	}
	
	public List<Filme> ordenarGeneros() throws IOException {
		return dao.ordenarGeneros();
	}
	
	public List<Filme> ordenarPopularidade() throws IOException {
		return dao.ordenarPopularidade();
	}
	
	public List<Filme> ordenarData() throws IOException {
		return dao.ordenarData();
	}
	
	@Transactional
	public void gravarImagem(ServletContext servletContext, Filme filme, MultipartFile file) throws IOException {
		if(!file.isEmpty()) {
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			String path = servletContext.getRealPath(servletContext.getContextPath());
			System.out.println("PATH" + path);
			path = path.substring(0, path.lastIndexOf(File.separatorChar));
			String nomeArquivo = "img" + filme.getId()+".jpg";
			filme.setPosterPath(nomeArquivo);
			atualizarFilme(filme);
			File destination = new File(path + File.separatorChar + "img" + File.separatorChar + nomeArquivo);
			if(destination.exists()) {
				destination.delete();
			}
			ImageIO.write(src, "jpg", destination);
		}
	}
	
	@Transactional
	public void baixarFilmesMaisPopulares() throws IOException {
		RestTemplate rest = new RestTemplate();
		Populares resultado = rest.getForObject(BASE_URL+POPULAR, Populares.class);
		System.out.println("Resultado: " + resultado);
		for(Movie movie:resultado.getResults()) {
			Filme filme = toFilme(movie);
			dao.inserirFilme(filme);
		}
	}
	
	@Transactional
	public void baixarLancamentos() throws IOException {
		RestTemplate rest = new RestTemplate();
		Lancamentos resultado = rest.getForObject(BASE_URL+UPCOMING, Lancamentos.class);
		System.out.println("Resultado: " + resultado);
		for(Movie movie:resultado.getResults()) {
			Filme filme = toFilme(movie);
			dao.inserirFilme(filme);
		}
	}

	private Filme toFilme(Movie movie) {
		RestTemplate rest = new RestTemplate();
		Creditos resultado = rest.getForObject(BASE_URL+"/"+movie.getId()+CREDITS, Creditos.class);
		System.out.println("Diretor: " + resultado);
		Filme filme = new Filme();
		int count = 0;
		for(Equipe equipe:resultado.getCrew()) {
			if(equipe.getJob().equals("Director")) {
				count++;
				if(count == 1) {
					filme.setDiretor(equipe.getName());
				} else {
					filme.setDiretor(filme.getDiretor() +"; "+ equipe.getName());
				}
			}
		}
		filme.setDataLancamento(movie.getRelease_date());
		filme.setDescricao(movie.getOverview());
		Genero genero = new Genero();
		try {
			genero.setId(movie.getGenre_ids()[0]);
		} catch (Exception e) {
			genero.setId(1);
		}
		filme.setGenero(genero);
		filme.setPopularidade(movie.getPopularity());
		filme.setPosterPath(POSTER_URL+movie.getPoster_path());
		filme.setTitulo(movie.getTitle());
		return filme;
	}

}
