package com.kh.opendata.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 에어코리아 대기오염 - 시도별 실시간 측정 조회 후 응답 데이터를 저장
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirVO {
	// 외부 API를 사용할 때는 String 타입으로 하는 것이 편하고
	// 데이터를 출력할 때 DTO에서 정확한 타입을 지정해주는 방식으로 사용할 수 있다.
	private String stationName; // 측정소 이름
	private String dataTime; // 측정 일시
	private String khaiValue; // 통합 대기환경 수치
	private String pm10Value; // 미세먼지 농도
	private String so2Value; // 아황산가스 농도
	private String coValue; // 일산화탄소 농도
	private String no2Value; // 이산화질소 농도
	private String o3Value; // 오존 농도
	
	/**
	 * Getter/Setter를 전부 사용하는 것보다 기본적으로 Getter를 사용하고
	 * 반드시 변경되는 것만 Setter를 따로 정의해서 사용하는 것이 좋다
	 * 
	 * => 왜?
	 * 객체 지향 디자인 원칙에 더 부합하다.
	 * 
	 * 구글링을 통해 검색해본 결과
	 * 
	 * - 객체의 책임 분담
	 * 		Setter를 사용하면 외부 객체가 객체의 상태를 직접 변경할 수 있습니다.
	 * 		이는 객체의 책임을 외부 객체로 넘기게 되어 객체의 역할과 책임이 모호해집니다.
	 * 		Setter를 최소화하고 필요한 경우에만 객체의 내부 로직을 통해 상태를 변경하도록 함으로써
	 * 		객체의 책임과 역할을 명확하게 정의할 수 있습니다. 
	 * 
	 * - 코드의 유연성 향상
	 * 		Setter를 최소화하면 객체의 내부 구현 방식이 외부 코드에 노출되지 않아,
	 * 		객체의 내부 구현을 변경해도 외부 코드에 영향을 미치지 않습니다. 이는 코드의 유연성을 높여줍니다. 
	 * 
	 * - 일관성 유지
	 * 		Setter를 통해 외부에서 객체의 상태를 변경할 경우, 객체의 상태가 일관성을 잃을 수 있습니다.
	 * 		Setter를 최소화하고 필요한 경우에만 객체의 내부 로직을 통해 상태를 변경하도록 함으로써 객체의 상태를 일관성 있게 유지할 수 있습니다. 
	 * 
	 * Getter는 객체의 정보를 읽는 데 필수적이지만, Setter는 객체의 상태 변경을 위해 필요한 경우에만 사용해야 합니다.
	 * Setter를 최소화하고 객체의 내부 로직을 통해 상태를 변경하도록 하면 객체의 정보 은닉을 강화하고 코드의 유연성과 유지보수성을 높일 수 있습니다. 
	 */
}
