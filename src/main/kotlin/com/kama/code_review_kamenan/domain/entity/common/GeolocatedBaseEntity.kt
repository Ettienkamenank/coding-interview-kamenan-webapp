package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class GeolocatedBaseEntity(var longitude: Double = 0.toDouble(),
                                    var latitude: Double = 0.toDouble()) : BaseEntity()
