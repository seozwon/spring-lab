package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello1")
    public String hello(Model model) {
    //    1. 정적 컨텐츠 방식
    //  스프링한테 물어봐, hello에 뭐 매핑되어있냐? 그럼 hello 라는 url 매칭시켜주고 있다해
    //  model 이라는 걸 만들어서 넘겨줘(매개변수 넣어주겠지)
        model.addAttribute("data", "hello!!");
    //  키 값 :data / value : hello!! 전달 => resource/templates/hello.html 로 전달
        return "hello";
    //  이 return 이 템플릿에 있는 hello.html 에 hello 랑 똑같아, 만약 이게 이름이 다르면? 안된다
    //  이 모델을 hello.html 에 넘기면서 렌더링 하라고 해
    //  컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(viewResolver)가 화면을 찾아서 처리한다
    //  스프링 부트 템플릿엔진 기본 viewName 매핑
    //  => 'resources:templates/' + (viewName) + '.html'
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        //    2. MVC 패턴 (모델, 뷰, 컨트롤러로 구분)
        model.addAttribute("name", name);
        return "hello-template";
        //    템플릿 엔진 : model 과 같은 것들을 가지고, 템플릿을 이용해서 화면 뷰를 조작하는 방식?
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        //    body 부분에 내가 데이터를 직접 넣어주겠다는 의미 => 물음표로 키 밸류 값 넣어줌

        //  name 을 지원이라고 하면 return 이 "hello 지원" 이라고 나옴
        return "hello " + name;
        //  => 'http://localhost:8080/hello-string?name=zwon' 으로 열어보면 그냥 문자 그대로 무식하게 hello zwon 이라고 나옴
        //  얘는 문자열 그대로 내보내주는 방식 => 이건 거의 쓸 일 없음...
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloapi(@RequestParam("name") String name) {
        //    이게 진짜 중요 !! -   3. API 방식
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        //  'http://localhost:8080/hello-api?name=zwon' 이거 실행하면? => json 형식으로 {"name":"zwon"} 이게 나옴
    }

    static class Hello {
        //  => 내장 클래스 (이걸 잘 안 써)
        //    이거는 다른데서 못봐, HelloController 에서만 참조 가능
        //    근데 /domain 에 Member.java 는 어디서든 가능
        private String name;
        public String getName() {  // getter : 꺼낼 때
            return name;
        }
        public void setName(String name) {   // setter : 넣을 때
            this.name = name;
        }
    }
}

// 정리
// 1. 정적 컨텐츠 : 파일을 그대로 내려준다.
// 2. MVC 패턴 : 모델, 뷰, 컨트롤러로 쪼개서 뷰를 템플릿 엔진으로 html을 좀 더 프로그래밍 해서 렌더링해서 그 html을 클라이언트에 내려준다
// 3. api 방식 : 객체(json)를 반환한다. 뷰 이런거 없이 그냥 객체만 보냄


// 컨트롤러 안에서 Hello 라는 객체를 만들어서 api 로 반환을 해
// 근데 도메인에 있는 멤버라는 클래스는 Hello 객체랑 다른거?
// 구조도 똑같고(id, name, getter, setter), 같은 내용 아님?
// 도메인에 있는 Member 는 디비에 있는거?
// 컨트롤러에 있는 반환용 객체는 그냥 반환할 때만 사용?