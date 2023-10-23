package com.nhomA.mockproject.mapper;

import com.nhomA.mockproject.dto.CartLineItemResponseDTO;
import com.nhomA.mockproject.entity.CartLineItem;

public interface CartLineItemMapper {
    CartLineItemResponseDTO toResponseDTO (CartLineItem cartLineItem);
}
