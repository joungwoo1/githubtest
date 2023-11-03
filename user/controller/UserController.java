package www.dream.bbs.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.dream.bbs.user.model.RecentMovieVO;
import www.dream.bbs.user.model.ReportVO;
import www.dream.bbs.user.model.UserVO;
import www.dream.bbs.user.service.UserService;

@RestController		// Container에 담기도록 지정
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	// /user/anonymous/listAllMember
	@GetMapping("/anonymous/listAllMember")
	// @PreAuthorize("hasAnyRole('manager')")
	public ResponseEntity<List<UserVO>> listAllMember() {
		return ResponseEntity.ok(userService.listAllMember());
	}

	// /user/anonymous/listReported
	@GetMapping("/anonymous/listReported")
	// @PreAuthorize("hasAnyRole('manager')")
	public ResponseEntity<List<ReportVO>> listReported() {
		return ResponseEntity.ok(userService.listReported());
	}

	// /user/anonymous/listAllRecentMovies?userId={userId}
	@GetMapping("/anonymous/listAllRecentMovies")
	public ResponseEntity<List<RecentMovieVO>> listAllRecentMovies(String userId) {
		return ResponseEntity.ok(userService.listAllRecentMovies(userId));
	}

	// /user/anonymous/listRecentMoviesGenre?userId={userId}
	@GetMapping("/anonymous/listRecentMoviesGenre")
	public ResponseEntity<List<String>> listRecentMoviesGenre(String userId) {
		return ResponseEntity.ok(userService.listRecentMoviesGenre(userId));
	}

	// /user/createManager
	@PostMapping("/createManager")
	public ResponseEntity<Integer> createManager(UserVO person) {
		return ResponseEntity.ok(userService.createManager(person));
	}
	
	// /user/anonymous/checkNick?nick=root
	@GetMapping("/anonymous/checkNick")
	public ResponseEntity<Boolean> checkNick(String nick) {
		return ResponseEntity.ok(userService.checkNick(nick));
	}

	// /user/anonymous/createMember
	@PostMapping("/anonymous/createMember")
	public ResponseEntity<Integer> createMember(@RequestBody UserVO person) {
		return ResponseEntity.ok(userService.createMember(person));
	}

	// /user/anonymous/updateMembership
	@PostMapping("/anonymous/updateMembership")
	public ResponseEntity<Integer> updateMembership(@RequestBody UserVO person) {
		return ResponseEntity.ok(userService.updateMembership(person));
	}

	// /user/anonymous/updatePenalty
	@PostMapping("/anonymous/updatePenalty")
	public ResponseEntity<Integer> updatePenalty(@RequestBody UserVO person) {
		return ResponseEntity.ok(userService.updatePenalty(person));
	}

	// /user/anonymous/givePenalty?userId={userId}
	@GetMapping("/anonymous/givePenalty")
	public ResponseEntity<Integer> givePenalty(String userId) {
		return ResponseEntity.ok(userService.givePenalty(userId));
	}

	// /user/anonymous/checkReport?replyId={replyId}
	@GetMapping("/anonymous/checkReport")
	public ResponseEntity<Integer> checkReport(String replyId) {
		return ResponseEntity.ok(userService.checkReport(replyId));
	}

	// /user/anonymous/inactiveAccount?userId={userId}
	@GetMapping("/anonymous/inactiveAccount")
	public ResponseEntity<Integer> inactiveAccount(String userId) {
		return ResponseEntity.ok(userService.inactiveAccount(userId));
	}

	// /user/anonymous/watchingMovie
	@PostMapping("/anonymous/watchingMovie")
	public ResponseEntity<Integer> watchingMovie(@RequestBody RecentMovieVO watching) {
		return ResponseEntity.ok(userService.watchingMovie(watching));
	}
}
