package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello1")
//  스프링한테 물어봐, hello에 뭐 매핑되어있냐? 그럼 hello 라는 url 매칭시켜주고 있다해
//  model 이라는 걸 만들어서 넘겨줘(매개변수 넣어주겠지)
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
//      키 값 :data / value : hello!! 전달 => resource/templates/hello.html 로 전달
        return "hello";
//      이 return 이 템플릿에 있는 hello.html 에 hello 랑 똑같아, 만약 이게 이름이 다르면? 안된다
//      이 모델을 hello.html 에 넘기면서 렌더링 하라고 해
//      컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(viewResolver)가 화면을 찾아서 처리한다
//      스프링 부트 템플릿엔진 기본 viewName 매핑
//      => 'resources:templates/' + (viewName) + '.html'
    }
}
