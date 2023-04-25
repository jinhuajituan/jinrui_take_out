package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.entity.AddressBook;
import com.jinli.jinrui.mapper.AddressBookMapper;
import com.jinli.jinrui.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
