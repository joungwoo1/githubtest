package www.dream.bbs.movie;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_movie_genre")
@NoArgsConstructor
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MovieGenreDTO {
	@Id
	private Integer movieId;
	private Integer genreId;
	private String genreName;
	
	public MovieGenreDTO(Integer movieId,  Integer genreId, String genreName) {
		this.movieId = movieId;
		this.genreId = genreId;
		this.genreName = genreName;
	}
	
}
