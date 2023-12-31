drop table T_comp_hierarch;
drop table T_TGT_TAG;
drop table T_TAG;
drop table T_reply;
drop table T_party;
drop table T_bulitine_board;

--id, name, descrip, post_cnt
create table T_bulitine_board(
	id			char(4) primary key,
	name		varchar(255) not null,
	descrip 	varchar(255),
	post_cnt	long default 0 comment '총 게시물 개수'
);

insert into T_bulitine_board(id, name, descrip)
values(NEXT_PK('S_bulitine_board'), '자유게시판', '자유롭죠');

--	T_party(id, descrim, name, nick, pwd, sex, alive, reg_dt, upt_dt)
create table T_party(
	id		char(4) primary key,
	descrim varchar(255) not null, /* 'Person', 'Organization' */
	name    varchar(255) not null,
	nick	varchar(255) not null comment 'login id 용도',
	pwd		varchar(255) not null,
	reg_dt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '회원가입일',
	upt_dt  TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment '최종 정보 수정일',
	/* Person */
	sex     boolean	comment '1 F, 0 M'
);
-- 로그인시 비교 대상인 암호까지 index에서 제공하여 table에 대한 접근을 방어하여 성능을 높임
create index idx_party_nick on T_party(nick);
drop index idx_party_nick on T_party;

	values('????', 'Organization', 'Dream Company', 'sys', 암호화하여'sys');
	
 (id, account_type, owner_id, respons_id, alive, reg_dt, upt_dt)
create table T_Accountability(
	id			char(4) primary key,
	account_type varchar(255),
	owner_id	char(4) comment '주인으로서',
	respons_id	char(4) comment '대상으로서',
	alive	boolean	DEFAULT true comment '활성 여부',
	reg_dt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '등록일',
	upt_dt  TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment '최종 정보 수정일'
);

/* 코드 유형 정의 */
create table T_CODE(
	Code_type	varchar(255) not null,
	code_val	varchar(255),
	validation_re varchar(255)
);
insert into T_CODE(Code_type, code_val) values('accountability type', 'manager');
insert into T_CODE(Code_type, code_val) values('accountability type', 'member');

insert into T_CODE(Code_type, code_val) values('contect point type', 'hand phone number');
insert into T_CODE(Code_type, code_val) values('contect point type', 'home address');
insert into T_CODE(Code_type, code_val, validation_re) values('contect point type', 'email', '[a-z0-9]+@[a-z]+\.[a-z]{2,3}');

insert into T_CODE(Code_type, code_val) values('rel target tag', 'post');
insert into T_CODE(Code_type, code_val) values('rel target tag', 'party');

--T_contact_Point(owner_id, cp_type, cp_val)
create table T_contact_Point(
	owner_id	char(4),
	cp_type		varchar(255),
	cp_val		varchar(255),
	primary key(owner_id, cp_type)
);


drop table T_reply;
--id, descrim, writer_id, TITLE, content, bb_id, genre, age_limit, star_score, read_cnt, reg_dt, upt_dt
create table T_reply(
	id			varchar(255) primary key,
	descrim 	varchar(255) not null comment 'reply, post 구분자',
	writer_id	char(4) comment '글쓴아이디',
	TITLE		varchar(255),
	content 	TEXT(65000),
	bb_id 		char(4) comment '상단 메뉴바 번호',
	genre		varchar(255) comment '장르 액션/무협 , 모험 , 판타지 , 공상 , 과학(SF) , 누아르, 전쟁, 코미디',
	age_limit	char(2) comment '나이제한 All 0, 12세 1, 15세 2, 18세이상 3',
	star_score	float default 0 comment '별점 0.5 ~ 5.0',
	read_cnt	int default 0 comment '조회수',
	reg_dt  	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	upt_dt  	TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	/* 아래 속성은 게시글 일때만 활용되는 */
);
create index idx_post_board on T_reply(bb_id);

--id, word, description, df
--통합 검색 체계
create table T_TAG(
	id			char(4) primary key,
	word		varchar(255),
	description	TEXT(65000)
);

--tgt_name, tgt_id, tag_id, tf
create table T_TGT_TAG(
	tgt_name	varchar(255),	/* post, party */
	tgt_id		char(4),
	tag_id		char(4),
	tf			int,
	primary key(tgt_name, tag_id, tgt_id) /* post*/
);
/* party */
create index idx_tgt_tag on T_TGT_TAG(tgt_name, tgt_id, tag_id);


/* top2bottom bottom2top */
create table T_comp_hierarch(
	id				char(4) primary key,
	comp_hierarch	varchar(255),
	kind			char(3)	/* t2b, b2t */
);	
create index idx_comp_hier on T_comp_hierarch(comp_hierarch);

insert into t_attach(owner_type, owner_id, uuid, path, name, type_name)
values();
create table t_attach(
	owner_type	varchar(255),	/* 테이블 이름 적는 곳 T_party, T_reply */
	owner_id	varchar(255),
	uuid        char(32),
	path		char(10),
	name		varchar(500),
	type_name   varchar(100),
	primary key(uuid)
);
create index idx_attach_owner on t_attach(owner_id);
create index idx_attach_path on t_attach(path);
