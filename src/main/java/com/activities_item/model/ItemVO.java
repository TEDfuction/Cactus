package com.activities_item.model;



import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.activities_category.model.CategoryVO;
import com.activities_photo.model.PhotoVO;
import com.activities_session.model.SessionVO;


@Entity
	@Table(name = "activity_item")
	public class ItemVO implements java.io.Serializable{
		private static final long serialVersionUID = 1L;
		
		public ItemVO() {
		}

	
		@Id
		@Column(name = "activity_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer activityId;

		@ManyToOne
		@JoinColumn(name = "activity_category_id", referencedColumnName = "activity_category_id")
		private CategoryVO categoryVO;

		//PK一對多
		@OneToMany(mappedBy = "activityPh", cascade = CascadeType.ALL)
		private Set<PhotoVO> aips;
		
		@Column(name = "activity_name")
		@NotEmpty(message="請勿空白")
		@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
		private String activityName;
		
		@Column(name = "activity_description")
		@NotEmpty(message="請勿空白")
		private String activityDescription;
		
		@Column(name = "activity_info")
		@NotEmpty(message="請勿空白")
		private String activityInfo;
		
		@Column(name = "activity_price")
		@NotNull(message="金額: 請勿空白")
//		@DecimalMin(value = "1", message = "金額: 不能小於{value}")
//		@DecimalMax(value = "99999", message = "金額: 不能超過{value}")
		private Integer activityPrice;
		
		@Column(name = "activity_state")
		private Boolean activityState;

		@OneToMany(mappedBy = "itemVO", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		@OrderBy("activitySessionId")
		private Set<SessionVO> sessionVOs;
		
		public Integer getActivityId() {
			return activityId;
		}

		public void setActivityId(Integer activityId) {
			this.activityId = activityId;
		}

		public Set<PhotoVO> getAips() {
			return aips;
		}

		public void setAips(Set<PhotoVO> aips) {
			this.aips = aips;
		}

		public String getActivityName() {
			return activityName;
		}

		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}

		public String getActivityDescription() {
			return activityDescription;
		}

		public void setActivityDescription(String activityDescription) {
			this.activityDescription = activityDescription;
		}

		public String getActivityInfo() {
			return activityInfo;
		}

		public void setActivityInfo(String activityInfo) {
			this.activityInfo = activityInfo;
		}

		public Integer getActivityPrice() {
			return activityPrice;
		}

		public void setActivityPrice(Integer activityPrice) {
			this.activityPrice = activityPrice;
		}

		public Boolean getActivityState() {
			return activityState;
		}

		public void setActivityState(Boolean activityState) {
			this.activityState = activityState;
		}


		public CategoryVO getCategoryVO() {
			return categoryVO;
		}

		public void setCategoryVO(CategoryVO categoryVO) {
			this.categoryVO = categoryVO;
		}

	public Set<SessionVO> getSessionVOs() {
		return sessionVOs;
	}

	public void setSessionVOs(Set<SessionVO> sessionVOs) {
		this.sessionVOs = sessionVOs;
	}
}



