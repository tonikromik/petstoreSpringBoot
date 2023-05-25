package com.petstore.service;

import com.petstore.dto.OrdersDTO;
import com.petstore.mapper.OrdersMapper;
import com.petstore.repository.StoreRepository;
import com.petstore.service.impl.StoreServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.petstore.service.StoreServiceTestFactory.ORDER;
import static com.petstore.service.StoreServiceTestFactory.ORDERS_DTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceImplTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private OrdersMapper ordersMapper;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    public void findById_ReturnOrdersDTO() {
        when(storeRepository.findAllFieldsById(1L)).thenReturn(Optional.of(ORDER));
        when(ordersMapper.toDTO(ORDER)).thenReturn(ORDERS_DTO);

        OrdersDTO ordersDTO = storeService.findById(1L);

        assertNotNull(ordersDTO);
        assertEquals(ORDERS_DTO, ordersDTO);
    }

    @Test
    public void findById_ThrowEntityNotFoundException() {
        when(storeRepository.findAllFieldsById(anyLong())).thenThrow(
                new EntityNotFoundException("Order with id '1L' not found."));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> storeService.findById(1L));
        assertEquals("Order with id '1L' not found.", exception.getMessage());
    }

    @Test
    public void saveOrder_ReturnOrdersDTO(){
        when(ordersMapper.toEntity(any())).thenReturn(ORDER);
        when(storeRepository.save(any())).thenReturn(ORDER);
        when(ordersMapper.toDTO(any())).thenReturn(ORDERS_DTO);

        OrdersDTO savedOrder = storeService.saveOrder(ORDERS_DTO);

        assertNotNull(savedOrder);
        assertEquals(ORDERS_DTO, savedOrder);
    }

    @Test
    public void delete_ReturnVoid(){
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(ORDER));
        doNothing().when(storeRepository).delete(ORDER);

        assertAll(()-> storeService.delete(1L));
        verify(storeRepository).delete(ORDER);
    }
}