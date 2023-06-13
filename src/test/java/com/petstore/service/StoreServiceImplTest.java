package com.petstore.service;

import com.petstore.dto.OrderDTO;
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
import static com.petstore.service.StoreServiceTestFactory.ORDER_DTO;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceImplTest {
    private static final String ORDER_NOT_FOUND = "Order with id '%d' not found.";
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private OrdersMapper ordersMapper;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    public void findById_ReturnOrdersDTO() {
        when(storeRepository.findAllFieldsById(1L)).thenReturn(Optional.of(ORDER));
        when(ordersMapper.toDTO(ORDER)).thenReturn(ORDER_DTO);

        OrderDTO orderDTO = storeService.findById(1L);

        verify(storeRepository).findAllFieldsById(1L);
        assertNotNull(orderDTO);
        assertEquals(ORDER_DTO, orderDTO);
    }

    @Test
    public void findById_ThrowEntityNotFoundException() {
        when(storeRepository.findAllFieldsById(anyLong())).thenThrow(
                new EntityNotFoundException(format(ORDER_NOT_FOUND, 1L)));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> storeService.findById(1L));

        verify(storeRepository).findAllFieldsById(1L);
        assertEquals(format(ORDER_NOT_FOUND, 1L), exception.getMessage());
    }

    @Test
    public void saveOrder_ReturnOrdersDTO() {
        when(ordersMapper.toEntity(any())).thenReturn(ORDER);
        when(storeRepository.save(any())).thenReturn(ORDER);
        when(ordersMapper.toDTO(any())).thenReturn(ORDER_DTO);

        OrderDTO savedOrder = storeService.saveOrder(ORDER_DTO);

        verify(storeRepository).save(ORDER);
        assertNotNull(savedOrder);
        assertEquals(ORDER_DTO, savedOrder);
    }

    @Test
    public void delete_ReturnVoid() {
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(ORDER));
        doNothing().when(storeRepository).delete(ORDER);

        assertAll(() -> storeService.delete(1L));
        verify(storeRepository).delete(ORDER);
    }
}