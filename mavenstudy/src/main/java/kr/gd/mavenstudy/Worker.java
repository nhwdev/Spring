package kr.gd.mavenstudy;

import org.springframework.stereotype.Component;

@Component // 객체화 됨
public class Worker {
    public void work(WorkUnit unit) {
        System.out.println(this + ": work" + unit);
    }
}
