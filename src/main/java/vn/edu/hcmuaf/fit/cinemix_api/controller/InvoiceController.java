package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.service.invoice.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(HttpResponse.success(invoices, "Lấy danh sách hóa đơn thành công!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getInvoiceById(@PathVariable Long id) throws BaseException {
        InvoiceDTO invoice = invoiceService.getInvocieById(id);
        return ResponseEntity.ok(HttpResponse.success(invoice, "Lấy thông tin hóa đơn thành công!"));
    }
}
