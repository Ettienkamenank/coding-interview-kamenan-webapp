package com.kama.code_review_kamenan.domain.account.port

interface UserDomain : IRegisterUser, IAuthenticateUser, IRequestUser, IManageServiceProvider,
    IManageCustomer, IManageUserForMobile