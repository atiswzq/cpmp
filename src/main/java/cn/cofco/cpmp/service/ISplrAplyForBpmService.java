package cn.cofco.cpmp.service;

import cn.cofco.cpmp.bpm.entity.PiResponse;

/**
 * Created by xsmiler on 2017/8/20.
 */
public interface ISplrAplyForBpmService {

    PiResponse rpl(String bpmSeqNo, String sucFlg, String memo);
}
