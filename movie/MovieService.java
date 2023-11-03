package www.dream.bbs.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	@Autowired
	private MovieMapper movieMapper;

	//영화 ID를 통해 영화 상세 정보를 가져오는것.
	public MovieDTO findById(String id) {
		return movieMapper.findById(id);
	}
	
	//인기 영화리스트 중복 안돼게 받는거.
	public int saveMovie(MovieDTO movieDTO) {
		int id = movieDTO.getId();
		boolean isNew = movieMapper.findByListId(id);
		if (isNew)
			return movieMapper.saveMovie(movieDTO);
		else {
			return 0;
		}
	}
	//영화 장르 정보 중복 안돼게 받는거.
	public int saveGenre(MovieGenreDTO movieGenreDTOs) {
		int movieId = movieGenreDTOs.getMovieId();
		int genreId = movieGenreDTOs.getGenreId();
		boolean isNews = movieMapper.findByMovieId(movieId, genreId);
		if (isNews)
			return movieMapper.saveGenre(movieGenreDTOs);
		else {
			return 0;
		}
	}

	/** 게시글 등록할때 영화타이틀 검색 */
	public List<MovieDTO> search(String search) {
		String[] arrSearch = search.split(" ");
		List<MovieDTO> listResult = movieMapper.searchBymovieId(arrSearch);
		return listResult;
	}
	
}
