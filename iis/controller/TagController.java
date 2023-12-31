package www.dream.bbs.iis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import www.dream.bbs.iis.model.TagVO;
import www.dream.bbs.iis.service.TagService;

@Controller 	// Container에 담기도록 지정
@RequestMapping("/tag")
public class TagController {
	@Autowired
	private TagService tagService;

	// /tag/getAll
	@GetMapping("/getAll")
	public ResponseEntity<List<TagVO>> getAll() {
		return new ResponseEntity<>(tagService.getAll(), HttpStatus.OK);
	}

	// /tag/0001
	@GetMapping("/{id}")
	public ResponseEntity<TagVO> getTag(@PathVariable String id) {
		Optional<TagVO> optionalTag = tagService.getTag(id);
		if (optionalTag.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(optionalTag.get(), HttpStatus.OK);
		}
	}

	@GetMapping("/{word}/{desc}")
	public ResponseEntity<TagVO> createTag(@PathVariable String word, @PathVariable String desc) {
		TagVO tagWithID = tagService.createTag(new TagVO(word, desc));
		return new ResponseEntity<>(tagWithID, HttpStatus.OK);
	}
}
