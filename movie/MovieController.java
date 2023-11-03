package www.dream.bbs.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.dream.bbs.board.model.BoardVO;
import www.dream.bbs.board.model.PostVO;
import www.dream.bbs.framework.model.DreamPair;
import www.dream.bbs.framework.model.PagingDTO;

@RestController		// Container에 담기도록 지정
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private MovieService movieService;

	// /movie/getMovie/937249 영화ID를 통해 해당 영화 상세정보 호출. //ID통한 검색기능(영화상세정보)
	@GetMapping("/getMovie/{id}")
	public ResponseEntity<MovieDTO> findById(@PathVariable String id) {
		return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
	}
	
	// /movie/search/"검색할단어" //검색기능
	@GetMapping("/search/{search}")
	public ResponseEntity<List<MovieDTO>> search(@PathVariable String search) {
		List<MovieDTO> result = movieService.search(search);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
	
}