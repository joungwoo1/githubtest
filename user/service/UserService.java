package www.dream.bbs.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import www.dream.bbs.user.mapper.UserMapper;
import www.dream.bbs.user.model.RecentMovieVO;
import www.dream.bbs.user.model.ReportVO;
import www.dream.bbs.user.model.UserVO;

@Service // Container에 담기도록 지정
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder pwdEnc;

	public List<UserVO> listAllMember() {
		return userMapper.listAllMember();
	}

	public List<ReportVO> listReported() {
		return userMapper.listReported();
	}

	public List<RecentMovieVO> listAllRecentMovies(String userId) {
		return userMapper.listAllRecentMovies(userId);
	}

	public List<String> listRecentMoviesGenre(String userId) {
		return userMapper.listRecentMoviesGenre(userId);
	}

	public int createManager(UserVO user) {
		user.encodePwd(pwdEnc);

		return 0;
	}

	/** 회원 가입 */
	public int createMember(UserVO user) {
		user.encodePwd(pwdEnc);
		List<UserVO> isNew = userMapper.isNew(user);
		if (isNew.isEmpty()) {
			userMapper.createUser(user);
		} else {
			UserVO oldUser = isNew.get(0);
			user.setId(oldUser.getId());
			userMapper.rejoinUser(user);
		}
		return 0;
	}

	public boolean checkNick(String nick) {
		return userMapper.isValidNick(nick);
	}

	public int updateMembership(UserVO user) {
		userMapper.updateMembership(user);
		return 0;
	}

	public int updatePenalty(UserVO user) {
		int point = userMapper.updatePenalty(user);
		return point;
	}

	public int givePenalty(String userId) {
		int point = userMapper.givePenalty(userId);
		return point;
	}

	public int checkReport(String replyId) {
		userMapper.checkReport(replyId);
		return 0;
	}

	public int inactiveAccount(String userId) {
		userMapper.inactiveAccount(userId);
		return 0;
	}

	public int watchingMovie(RecentMovieVO watching) {
		boolean isNew = userMapper.findRecentMovie(watching);
		if (isNew) {
			userMapper.createRecentMovie(watching);
		} else {
			userMapper.updateRecentMovie(watching);
		}
		return 0;
	}
}
