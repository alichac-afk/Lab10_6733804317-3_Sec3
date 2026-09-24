package com.example.lab10;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

/**
 * Lab10ApplicationTests — ทดสอบ Reactive code
 *
 * ✅ test findById() ทำเสร็จแล้วเป็นตัวอย่าง
 * ✅ TODO: เพิ่ม test สำหรับ method ที่นักศึกษาทำเอง (ทำเสร็จเรียบร้อย)
 *
 * StepVerifier — วิธีทดสอบ Mono/Flux:
 *   StepVerifier.create(mono/flux)
 *     .expectNext(value)     ← คาดหวังค่าที่ได้
 *     .expectNextCount(n)    ← คาดหวังจำนวน element
 *     .verifyComplete()      ← ยืนยัน onComplete
 *     .verifyError()         ← ยืนยัน onError
 */
@SpringBootTest
class Lab10ApplicationTests {

    @Autowired
    private ProductRepository repository;

    // ══════════════════════════════════════════════════════
    // ✅ ตัวอย่าง test — ศึกษาแล้วเพิ่ม test เอง
    // ══════════════════════════════════════════════════════

    @Test
    void contextLoads() {
        // Spring Application Context โหลดสำเร็จ
    }

    @Test
    void testFindById_found() {
        // ✅ ตัวอย่าง: ทดสอบ findById ที่พบข้อมูล
        StepVerifier.create(repository.findById("1"))
                .expectNextMatches(p -> p.getName().contains("iPhone"))
                .verifyComplete();
    }

    @Test
    void testFindById_notFound() {
        // ✅ ตัวอย่าง: ทดสอบ findById ที่ไม่พบข้อมูล
        StepVerifier.create(repository.findById("999"))
                .verifyComplete(); // Mono.empty() → onComplete ทันที
    }

    // ══════════════════════════════════════════════════════
    // ✅ เพิ่ม test ด้านล่างเรียบร้อยแล้ว
    // ══════════════════════════════════════════════════════

    @Test
    void testFindAll() {
        // ทดสอบว่า findAll() คืน Flux ที่มีข้อมูลครบ 3 รายการ
        StepVerifier.create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testSave() {
        // สร้าง Product ใหม่สำหรับทดสอบบันทึก
        Product newProduct = new Product("4", "iPad Pro", "Electronics", "Apple", 10, 39900.0, "MEMBER");

        // ทดสอบว่า save() สามารถบันทึกและคืนค่า Product ที่ตรงกันได้อย่างถูกต้อง
        StepVerifier.create(repository.save(newProduct))
                .expectNextMatches(p -> p.getId().equals("4") && p.getName().equals("iPad Pro"))
                .verifyComplete();
    }

    @Test
    void testFindByCategory() {
        // ทดสอบ findByCategory("Electronics") ซึ่งควรได้ข้อมูล 3 รายการ (ตามข้อมูลใน repository)
        StepVerifier.create(repository.findByCategory("Electronics"))
                .expectNextCount(3)
                .verifyComplete();
    }
}