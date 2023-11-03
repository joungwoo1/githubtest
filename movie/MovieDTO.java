package www.dream.bbs.movie;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_movie")
@NoArgsConstructor
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MovieDTO {

	private boolean adult;
	@Id
	private Integer id;
	private String title;
	private Double popularity;
	private String overview;
	private Double voteAverage;
	private Integer voteCount;

	public MovieDTO(Integer id) {
		this.id = id;
	}

	public MovieDTO(Boolean adult, Integer id, String title, Double popularity,
			String overview, Double voteAverage, Integer voteCount) {
		this.adult = adult;
		this.id = id;
		this.title = title;
		this.popularity = popularity;
		this.overview = overview;
		this.voteAverage = voteAverage;
		this.voteCount = voteCount;
	}
	
}