package www.dream.bbs.user.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReportVO {
	private String replyId;
	private String reporterId;
	private String reportedId;
	private String content;
	private boolean checked; // 기본 0, 처리후 1로 변경

	private int cnt;

	private List<ReportVO> listReport = new ArrayList<>();

	public ReportVO(String replyId, String reportedId, String content, int cnt) {
		this.replyId = replyId;
		this.reportedId = reportedId;
		this.content = content;
		this.cnt = cnt;
	}
}
