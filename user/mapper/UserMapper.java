package www.dream.bbs.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import www.dream.bbs.user.model.RecentMovieVO;
import www.dream.bbs.user.model.ReportVO;
import www.dream.bbs.user.model.UserVO;

@Mapper	// Container에 담기도록 지정
public interface UserMapper {
	// 관리자 입장에서 회사의 발전성을 보기 위하여 DAU(Daily Active Users) - Daily Active/New/Out Member count
	// 추세: 막대그래프
	// WAU, MAU
	
	// LRCUD 순서로 함수들을 배치하여 빠르게 따라갈(추적성) 수 있도록 합니다.

	public List<UserVO> listAllMember();

	public List<ReportVO> listReported();

	public List<RecentMovieVO> listAllRecentMovies(String id);

	public List<String> listRecentMoviesGenre(String userId);

	public UserVO findByNick(String nick);
	
	public boolean isValidNick(String nick);

	public List<UserVO> isNew(UserVO user);

	public boolean findRecentMovie(RecentMovieVO watching);
	
	public int createUser(UserVO user);

	public int createRecentMovie(RecentMovieVO watching);

	public int rejoinUser(UserVO user);

	public int updateMembership(UserVO user);

	public int updatePenalty(UserVO user);

	public int givePenalty(String id);

	public int checkReport(String id);

	public int inactiveAccount(String id);

	public int updateRecentMovie(RecentMovieVO watching);

	/** 회원 탈퇴 처리의 전략은?
	public int updateMember(PersonVO member);

	 * isActive !
	public int deactivateParty(PartyVO party);
	 * record delete (X) */
}
