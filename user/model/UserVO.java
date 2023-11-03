package www.dream.bbs.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class UserVO implements UserDetails {
	private String id;
	private String accountType; // memberx, membero, manager
	private String name;
	private String nick; // login id 용도, 회원탈퇴시 더미아이디로 변경
	private String pwd;
	private Date birth; // 연령확인용 생년월일
	private boolean sex; // 여자는 true, 남자는 false
	private String address; // 지역(구)
	private String email; // 인증용 이메일
	private Date membership; // 멤버쉽만료일. 지날경우 멤버쉽종료
	private int penalty; // 벌점
	private boolean active; // 기본 true, 탈퇴시 false으로 변경, 일정기간안에 복귀하면 다시 true로, 시간초과시 정보완전삭제

	private List<UserVO> listUser = new ArrayList<>();

	public UserVO(String id, String accountType, String name, String nick, String pwd, Date birth, boolean sex, String address, String email, Date membership, int penalty, boolean active) {
		this.id = id;
		this.accountType = accountType;
		this.name = name;
		this.nick = nick;
		this.pwd = pwd;
		this.birth = birth;
		this.sex = sex;
		this.address = address;
		this.email = email;
		this.membership = membership;
		this.penalty = penalty;
		this.active = active;
	}

	public void encodePwd(PasswordEncoder pwdEnc) {
		pwd = pwdEnc.encode(pwd);
	}

	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public String getUsername() {
		return nick;
	}

	public String getAccountType() {
		return accountType;
	}

	public Date getBirth() {
		return birth;
	}

	public Date getMembership() {
		return membership;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return listUser.stream().map(UserVO::getAuthority).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return listUser.stream().filter(UserVO::isActive).count() > 0;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public SimpleGrantedAuthority getAuthority() {
		return new SimpleGrantedAuthority(accountType);
	}
}
