package com.moo.cart.service;

import com.moo.cart.api.ErrorEnum;
import com.moo.cart.api.ItemDTO;
import com.moo.cart.api.exception.BadRequestException;
import com.moo.cart.models.Item;
import com.moo.cart.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

/**
 * This service is validate the request DTO object to the valid Item.
 */

@Service
public class Validator
{

    @Inject
    protected ProductService productService;

    public boolean validateProductCode(String code)
    {
        return (productService.getProduct(code).isPresent()) ? true : false;
    }

    public Item validateItemDto(ItemDTO itemDTO){

        if(StringUtils.isEmpty(itemDTO.getCode())
         || StringUtils.isEmpty(itemDTO.getQuantity())
        ) throw new BadRequestException("DATA INVALID", ErrorEnum.API_ERROR_INVALID_REQUEST_BODY);

        if(!validateProductCode(itemDTO.getCode())) throw new BadRequestException("PRODUCT CODE", ErrorEnum.API_ERROR_PRODUCT_CODE_NOT_CORRECT);

        return dtoToItem(itemDTO);
    }

    private Item dtoToItem(ItemDTO itemDTO) {
        Product product = productService.getProduct(itemDTO.getCode()).get();
        return new Item(product,itemDTO.getQuantity());
    }
}