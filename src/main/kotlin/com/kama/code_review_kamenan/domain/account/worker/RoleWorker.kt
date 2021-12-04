package com.kama.code_review_kamenan.domain.account.worker

import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.entity.common.Role
import com.kama.code_review_kamenan.infrastructure.db.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class RoleWorker : RoleDomain {

    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun save(model: Role): Role {
        return roleRepository.save(model)
    }

    override fun count(): Long = roleRepository.count()

    override fun findByName(name: String): Optional<Role> = roleRepository.findByName(name)

    override fun findAll(): List<Role> = roleRepository.findAll()

    override fun findById(id: Long): Optional<Role> = roleRepository.findById(id)

}