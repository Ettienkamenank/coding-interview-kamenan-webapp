package com.kama.code_review_kamenan.application.bootstrap

import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain

object ActivityAreaBootstrap {

    fun seed(activityAreaDomain: ActivityAreaDomain) {
        if (activityAreaDomain.count() == 0L) {
            activityAreaDomain.save(ActivityArea("Plombier"))
            activityAreaDomain.save(ActivityArea("Menuisier"))
            activityAreaDomain.save(ActivityArea("Carreleur"))
            activityAreaDomain.save(ActivityArea("Couturier"))
            activityAreaDomain.save(ActivityArea("Peintre"))
            activityAreaDomain.save(ActivityArea("Menagère"))
            activityAreaDomain.save(ActivityArea("Nettoyeur"))
            activityAreaDomain.save(ActivityArea("Cuisinier"))
            activityAreaDomain.save(ActivityArea("Jardinier"))
            activityAreaDomain.save(ActivityArea("Electricien"))
            activityAreaDomain.save(ActivityArea("Infirmier"))
        }
    }

}