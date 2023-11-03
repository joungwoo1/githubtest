package www.dream.bbs.movie;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.dream.bbs.framework.model.PagingDTO;

@Mapper	// Container에 담기도록 지정
public interface MovieMapper {
	//영화 ID를 통해 영화 상세 정보를 호출
	public MovieDTO findById(String id);
	
	//영화 리스트를 저장, 리스트 내에 영화ID 확인 후 중복 시 패스시키는 부분
	public int saveMovie(MovieDTO movieDTOs);
	public boolean findByListId(int id);
	
	//영화상세정보 내 장르정보 저장, 장르정보 내에 장르ID 확인 후 중복 시 패스시키는 부분
	public int saveGenre(MovieGenreDTO movieGenreDTOs); 
	public boolean findByMovieId(int movieId, int genreId);

	public List<MovieDTO> searchBymovieId(@Param("arrSearch") String[] arrSearch);

}
