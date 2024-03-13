package com.kh.finalproject.finalProject.restcontroller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.finalProject.entity.category;
import com.kh.finalproject.finalProject.entity.kit;
import com.kh.finalproject.finalProject.entity.wish;
import com.kh.finalproject.finalProject.repository.categoryRepository;
import com.kh.finalproject.finalProject.repository.kitRepository;
import com.kh.finalproject.finalProject.repository.wishRepository;
import com.kh.finalproject.finalProject.service.ApiService;
import com.kh.finalproject.finalProject.vo.ItemData;
import com.kh.finalproject.finalProject.vo.categoryVo;
import com.kh.finalproject.finalProject.vo.info2item;
import com.kh.finalproject.finalProject.vo.infoitem;
import com.kh.finalproject.finalProject.vo.selectItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class addrestcontroller {

	@Autowired
	private ApiService apiService;

	@Autowired
	private kitRepository kitrepository;

	@Autowired
	private wishRepository wishrepository;
	
	@Autowired
	private categoryRepository categoryrepository;

	@PostMapping("/search/searchModal")
	public ResponseEntity<?> searchModal(@RequestParam("keyword") String keyword) {

		log.info("searchModal{}", keyword);

		ArrayList<infoitem> list = apiService.getInfoItemList(keyword);
		ArrayList<info2item> list2 = apiService.getInfo2ItemList(keyword);

		int size = Math.min(list.size(), list2.size());

		ArrayList<Map<String, Object>> combinedList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Map<String, Object> combinedItem = new HashMap<>();
			combinedItem.put("item", list.get(i));
			combinedItem.put("item2", list2.get(i));

			combinedList.add(combinedItem);
			log.info("combinedItem{}", combinedItem);
		}

		if (combinedList == null) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없음");
		}

		log.info("{}", combinedList);

		return ResponseEntity.ok(combinedList);
	}

	@PostMapping("/enroll")
	public String enrollItem(@RequestBody ItemData itemData) {
		// ItemData에서 필요한 데이터 추출
		String itemCategory = itemData.getItemCategory();
		String itemName = itemData.getItemName();
		String packagingUnit = itemData.getPackagingUnit();
		Date registerDate = itemData.getRegisterDate();

		// Kit 엔티티 생성 및 데이터 설정
		kit kit = new kit();
		kit.setDivision(itemCategory); // 예시로 ItemData의 itemCategory를 kit 엔티티의 division에 매핑
		kit.setKitName(itemName); // 예시로 ItemData의 itemName을 kit 엔티티의 kitName에 매핑
		kit.setPackaging(packagingUnit); // 예시로 ItemData의 packagingUnit을 kit 엔티티의 packaging에 매핑
		kit.setEndDate(registerDate); // 현재 날짜를 kit 엔티티의 endDate에 설정

		// Kit 엔티티 저장
		kitrepository.save(kit);

		return "redirect:/main";
	}

	@PostMapping("/select")
	public String select(@RequestBody selectItem selectitem) {
		// ItemData에서 필요한 데이터 추출
		Date endDate = selectitem.getEndDate();
		int count = selectitem.getCount();
		String wishName = selectitem.getWishName();
		String memImg = selectitem.getMemImg();
		String wishFun = selectitem.getWishFun();

		// 시간 부분을 0으로 설정하여 날짜만 가져오기
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		endDate = cal.getTime();

		// endDate를 "년-월-일" 형식의 문자열로 변환
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedEndDate = sdf.format(endDate);

		// 오늘의 날짜 가져오기
		Date today = new Date();

		long differenceInMilliseconds = endDate.getTime() - today.getTime();
		long differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);

		// 남은 일수 계산
		int remainDate = (int) differenceInDays;

		// Kit 엔티티 생성 및 데이터 설정
		wish wish = new wish();
		wish.setEndDate(formattedEndDate);
		wish.setRemainDate(remainDate);
		wish.setCount(count);
		wish.setMemImg(memImg);
		wish.setWishName(wishName);
		wish.setWishFun(wishFun);

		// Kit 엔티티 저장
		wishrepository.save(wish);

		return "redirect:/main";
	}

	// 키트 삭제 요청 처리
	@GetMapping("/delete/{no}")
	public String deleteKit(@PathVariable Long no) {
		System.out.println(no);

		kitrepository.deleteById(no);

		return "redirect:/main";
	}

	// wish 삭제 요청 처리
	@GetMapping("/deletewish/{no}")
	public String deleteWish(@PathVariable Long no) {
		System.out.println(no);

		wishrepository.deleteById(no);

		return "redirect:/main";
	}

	@PostMapping("/sendButtonId")
    public String receiveButtonId(@RequestParam String buttonId, Model model) {
        // 받은 버튼 ID 값을 처리하는 로직을 작성합니다.
        // 여기서는 간단히 콘솔에 출력하는 예시를 보여줍니다.
        System.out.println("받은 버튼 ID 값: " + buttonId);

        category categ = new category();
        categ.setCategoryName(buttonId);

       categoryrepository.save(categ);
        // 클라이언트에게 응답을 보냅니다.
       
        return "redirect:/main";
    }
	
	@GetMapping("/deleteCategory/{no}")
    public String deleteCategory(@PathVariable Long no) {
   
		System.out.println("delect"+no);
		
		categoryrepository.deleteById(no);
		
		return "redirect:/main";
    }
	
	
	
	
}
