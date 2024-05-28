package com.activities_photo.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("photoservice")
public class PhotoService {
	
	@Autowired
	PhotoRepository photoRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addPhoto(PhotoVO photoVO) {
		photoRepository.save(photoVO);
		
	}
	
	public void updatePhoto(PhotoVO photoVO) {
		photoRepository.save(photoVO);
	}
	
	public void deletePhoto(Integer activityPhotoId) {
		if (photoRepository.existsById(activityPhotoId)) {
			photoRepository.deleteById(activityPhotoId);
		}
	}
	
	public PhotoVO getOnePhoto(Integer activityPhotoId) {
		System.out.println("------------------------------------------");
		System.out.println(photoRepository==null);
		System.out.println("------------------------------------------");
		Optional<PhotoVO> optional = photoRepository.findById(activityPhotoId);
		return optional.orElse(null);
	}
	
	public List<PhotoVO> getAll(){
		System.out.println("======================================================");
		System.out.println(photoRepository==null);
		System.out.println("=======================================================");
		return photoRepository.findAll();
	}
    // 先從最初的網頁按下按鈕後可以找出對應 PhotoId的值
	public PhotoVO findById(Integer activityPhotoId) {
		Optional<PhotoVO> Optional= photoRepository.findById(activityPhotoId);
		return Optional.orElse(null);
	}
	

//	public List<PhotoVO> getAll(Map<String, String[]> map){
//		return Photo_Compositegory.getAllPhotoVOs(map, sessionFactory.openSession());
//	}

}
