package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasteryService {

    @Autowired
    MasteryRepository masteryRepository;



}
