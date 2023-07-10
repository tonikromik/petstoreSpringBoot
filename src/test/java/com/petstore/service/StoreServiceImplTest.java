package com.petstore.service;

import com.petstore.dto.OrderDTO;
import com.petstore.mapper.OrderMapper;
import com.petstore.repository.StoreRepository;
import com.petstore.service.impl.StoreServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.petstore.testdatafactory.StoreTestFactory.TEST_ORDER;
import static com.petstore.testdatafactory.StoreTestFactory.TEST_ORDER_DTO;
import static com.petstore.service.impl.StoreServiceImpl.ORDER_NOT_FOUND;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceImplTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private OrderMapper ordersMapper;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    public void findById_ReturnOrdersDTO() {
        when(storeRepository.findAllFieldsById(1L)).thenReturn(Optional.of(TEST_ORDER));
        when(ordersMapper.toDTO(TEST_ORDER)).thenReturn(TEST_ORDER_DTO);

        OrderDTO orderDTO = storeService.findById(1L);

        verify(storeRepository).findAllFieldsById(1L);
        assertNotNull(orderDTO);
        assertEquals(TEST_ORDER_DTO, orderDTO);
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
        when(ordersMapper.toEntity(any())).thenReturn(TEST_ORDER);
        when(storeRepository.save(any())).thenReturn(TEST_ORDER);
        when(ordersMapper.toDTO(any())).thenReturn(TEST_ORDER_DTO);

        OrderDTO savedOrder = storeService.saveOrder(TEST_ORDER_DTO);

        verify(storeRepository).save(TEST_ORDER);
        assertNotNull(savedOrder);
        assertEquals(TEST_ORDER_DTO, savedOrder);
    }

    @Test
    public void delete_ReturnVoid() {
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(TEST_ORDER));
        doNothing().when(storeRepository).delete(TEST_ORDER);

        assertAll(() -> storeService.delete(1L));
        verify(storeRepository).delete(TEST_ORDER);
    }
}