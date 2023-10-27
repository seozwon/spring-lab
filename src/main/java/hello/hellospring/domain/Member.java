package hello.hellospring.domain;

public class Member {
    private Long id;
//  ID 식별자, 어떤 임의의 값 - DB 또는 데이터에 저장하든 하는 임의의 값, 고객이 정하는게 아닌 데이터를 구분하기 위해 시스템이 저장하는 ID

//    getter setter => 자동 생성 (맥북 기준 Ctrl + Enter)
    private String name;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {

        this.name = name;
    }
}
// spring 어디서든 접근 가능

