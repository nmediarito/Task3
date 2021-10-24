package csci318.demo.controller;

import csci318.demo.service.BrandInteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandQueryController {

    @Autowired
    BrandInteractiveQuery brandInteractiveQuery;

    @GetMapping("/brand/{brandName}/quantity")
    long getBrandQuantityByName(@PathVariable String brandName) {
        return brandInteractiveQuery.getBrandQuantity(brandName);
    }

    @GetMapping("/brands/all")
    List<String> getAllBrands() {
        return brandInteractiveQuery.getBrandList();
    }


    @GetMapping("/brand/{brandName}/equipments")
    List<String> getAllEquipmentsByBrand(@PathVariable String brandName) {
        return  brandInteractiveQuery.getEquipmentListByBrand(brandName);
    }

    @GetMapping("/brand/all-equipments")
    List<String> getAllEquipments() {
        return  brandInteractiveQuery.getEquipmentList();
    }

}
