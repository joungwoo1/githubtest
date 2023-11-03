package www.dream.bbs.webclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import www.dream.bbs.movie.MovieDTO;
import www.dream.bbs.movie.MovieGenreDTO;
import www.dream.bbs.movie.MovieService;

@Service
@PropertySource("classpath:application.properties")
public class WebClient4Movies {

	@Value("${tmdb-admin-key}")
	private String api_key;

	@Autowired
	private MovieService movieService;
	
	//@Scheduled(cron = "0 0 15 * * *") 매일 오후 3시마다 갱신
	@Scheduled(fixedRate = 10000) // 10초에 한번씩 갱신
	public void loadMovie() {

		// themoviedb.org에서 movie/popular 에 따른 정보 획득
		// https://api.themoviedb.org/3/movie/popular?api_key=a158e2a9424bc69fec449dcaeb82aba8&language=en&page={i}
		// https://api.themoviedb.org/3/movie/157336?api_key=2a98cbe1fa65b5daaabc0522192e19f3

		WebClient webClient = WebClient.builder().baseUrl("https://api.themoviedb.org")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		String result = webClient.get().uri("/3/movie/popular?api_key=" + api_key + "&language=ko&page=1").retrieve()
				.bodyToMono(String.class).block();
		String secondResult = webClient.get().uri("3/movie/299054?api_key=" + api_key).retrieve()
				.bodyToMono(String.class).block();

		Map<String, Object> map = this.jsonToMap(result);

		// int totalPages = (int)map.get("total_pages"); //추후 데이터가 허용되어 더 많은 값을 받을 수
		// 페이지를 1부터 500까지 순차적으로 돌린다.
		for (int pageNo = 1; pageNo <= 500; pageNo++) { // 500페이지까지만 제공받음
			// 영화 인기리스트 Url
			String subUri = "/3/movie/popular?api_key=" + api_key + "&language=ko&page=" + pageNo;
			result = webClient.get().uri(subUri).retrieve().bodyToMono(String.class).block();
			map = this.jsonToMap(result);

			// 위에 URL에서 받은 것을 List로 만든다.
			List movies = (List) map.get("results");
			// 그걸 오브젝트 obj라 하고, Map으로 만든다.
			for (Object obj : movies) {
				Map movie = (Map) obj;
				// 영화 성인등급여부. 영화 ID, 영화 TITLE, 영화 줄거리, 영화 투표횟수, 영화 투표평균평점
				Boolean movieAdult = (Boolean) movie.get("adult");
				Integer movieId = (Integer) movie.get("id");
				String movieTitle = (String) movie.get("title");
				String movieOverView = (String) movie.get("overview");
				Integer movieVoteCount = (Integer) movie.get("vote_count");

				// instanceof를 이용한 true, false 확인 후 if문으로 상황 맞춰 형변환.
				// Integer를 Double이 될 수 있도록, 기본 값을 0.0으로 주고 마지막에 1.0을 곱한다.
				Double moviePopularity = 0.0;
				Object ObjectPopularity = movie.get("popularity");
				if (ObjectPopularity instanceof Double) {
					moviePopularity = ((Double) movie.get("popularity"));
				} else if (ObjectPopularity instanceof Integer) {
					Integer popularity = (Integer) movie.get("popularity");
					moviePopularity = popularity * 1.0;
				}
				Double movieVoteAverage = 0.0;
				Object ObjectVoteAverage = movie.get("vote_average");
				if (ObjectVoteAverage instanceof Double) {
					movieVoteAverage = ((Double) movie.get("vote_average"));
				} else if (ObjectVoteAverage instanceof Integer) {
					Integer voteAverage = (Integer) movie.get("vote_average");
					movieVoteAverage = voteAverage * 1.0;
				}
				MovieDTO movieDTO = new MovieDTO(movieAdult, movieId, movieTitle, moviePopularity, movieOverView,
						movieVoteAverage, movieVoteCount);
				movieService.saveMovie(movieDTO);

				String secondSubUri = "3/movie/" + movieId + "?api_key=" + api_key;

				secondResult = webClient.get().uri(secondSubUri).retrieve().bodyToMono(String.class).block();
				map = this.jsonToMap(secondResult);

				List secondmovies = (List) map.get("genres");
				for (Object objs : secondmovies) {
					Map secondmovie = (Map) objs;
					Integer genreId = (Integer) secondmovie.get("id");
					String genreName = (String) secondmovie.get("name");

					MovieGenreDTO movieGenreDTOs = new MovieGenreDTO(movieId, genreId, genreName);
					movieService.saveGenre(movieGenreDTOs);
				}
			}
		}

	}

	private Map<String, Object> jsonToMap(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
		};
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<>();
	}
}