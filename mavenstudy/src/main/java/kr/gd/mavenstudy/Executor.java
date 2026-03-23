package kr.gd.mavenstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component : 현재 클래스를 객체화 함. 관련된 객체의 주입도 완료됨
@Component
public class Executor {
    @Autowired // 컨테이너 중 Worker 타입의 객체를 worker 변수에 저장. Worker 클래스는 객체화가 되어야 함
    private Worker worker;

    public void addUnit(WorkUnit unit) {
        worker.work(unit);
    }
}
