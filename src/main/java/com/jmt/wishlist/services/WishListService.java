package com.jmt.wishlist.services;

import com.jmt.member.MemberUtil;
import com.jmt.wishlist.constants.WishType;
import com.jmt.wishlist.entities.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final MemberUtil memberUtil;

    public void add(Long seq, WishList type) {

    }

    public void remove(Long seq, WishList type) {

    }

    public List<Long> getList(WishList type) {

        return null;
    }
}
