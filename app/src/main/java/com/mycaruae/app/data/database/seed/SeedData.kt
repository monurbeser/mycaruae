package com.mycaruae.app.data.database.seed

import com.mycaruae.app.data.database.entity.BrandEntity
import com.mycaruae.app.data.database.entity.EmirateEntity

object SeedData {

    val emirates = listOf(
        EmirateEntity("dubai", "Dubai", "Roads and Transport Authority (RTA)", "https://www.rta.ae", "https://www.rta.ae/wps/portal/rta/ae/home/services/drivers-and-vehicles/vehicle-testing", "800 9090"),
        EmirateEntity("abu_dhabi", "Abu Dhabi", "Integrated Transport Centre (ITC)", "https://www.tamm.abudhabi", "https://www.tamm.abudhabi/en/aspects-of-life/transportation/vehicles", "800 850"),
        EmirateEntity("sharjah", "Sharjah", "Sharjah Roads and Transport Authority", "https://www.shjmun.gov.ae", null, "06 565 6677"),
        EmirateEntity("ajman", "Ajman", "Ajman Transport Authority", "https://www.ajman.ae", null, "06 701 1111"),
        EmirateEntity("umm_al_quwain", "Umm Al Quwain", "UAQ Government - Transport Department", "https://uaq.ae", null, "06 765 5882"),
        EmirateEntity("ras_al_khaimah", "Ras Al Khaimah", "RAK Transport Authority", "https://www.raktda.com", null, "07 228 8888"),
        EmirateEntity("fujairah", "Fujairah", "Fujairah Transport Authority", "https://www.fujairah.ae", null, "09 222 7000"),
    )

    val sampleBrands = listOf(
        BrandEntity("brand_toyota", "Toyota", 1),
        BrandEntity("brand_nissan", "Nissan", 2),
        BrandEntity("brand_hyundai", "Hyundai", 3),
        BrandEntity("brand_kia", "Kia", 4),
        BrandEntity("brand_mitsubishi", "Mitsubishi", 5),
        BrandEntity("brand_honda", "Honda", 6),
        BrandEntity("brand_ford", "Ford", 7),
        BrandEntity("brand_chevrolet", "Chevrolet", 8),
        BrandEntity("brand_mercedes", "Mercedes-Benz", 9),
        BrandEntity("brand_bmw", "BMW", 10),
        BrandEntity("brand_lexus", "Lexus", 11),
        BrandEntity("brand_audi", "Audi", 12),
        BrandEntity("brand_porsche", "Porsche", 13),
        BrandEntity("brand_land_rover", "Land Rover", 14),
        BrandEntity("brand_jeep", "Jeep", 15),
        BrandEntity("brand_mazda", "Mazda", 16),
        BrandEntity("brand_suzuki", "Suzuki", 17),
        BrandEntity("brand_vw", "Volkswagen", 18),
        BrandEntity("brand_infiniti", "Infiniti", 19),
        BrandEntity("brand_renault", "Renault", 20),
        BrandEntity("brand_gmc", "GMC", 21),
        BrandEntity("brand_peugeot", "Peugeot", 22),
        BrandEntity("brand_volvo", "Volvo", 23),
        BrandEntity("brand_mg", "MG", 24),
        BrandEntity("brand_tesla", "Tesla", 25),
        BrandEntity("brand_isuzu", "Isuzu", 26),
        BrandEntity("brand_dodge", "Dodge", 27),
        BrandEntity("brand_chrysler", "Chrysler", 28),
        BrandEntity("brand_ram", "RAM", 29),
        BrandEntity("brand_cadillac", "Cadillac", 30),
        BrandEntity("brand_lincoln", "Lincoln", 31),
        BrandEntity("brand_genesis", "Genesis", 32),
        BrandEntity("brand_jaguar", "Jaguar", 33),
        BrandEntity("brand_maserati", "Maserati", 34),
        BrandEntity("brand_ferrari", "Ferrari", 35),
        BrandEntity("brand_lamborghini", "Lamborghini", 36),
        BrandEntity("brand_bentley", "Bentley", 37),
        BrandEntity("brand_rolls_royce", "Rolls-Royce", 38),
        BrandEntity("brand_aston_martin", "Aston Martin", 39),
        BrandEntity("brand_alfa_romeo", "Alfa Romeo", 40),
        BrandEntity("brand_skoda", "Skoda", 41),
        BrandEntity("brand_opel", "Opel", 42),
        BrandEntity("brand_citroen", "Citroen", 43),
        BrandEntity("brand_geely", "Geely", 44),
        BrandEntity("brand_haval", "Haval", 45),
        BrandEntity("brand_chery", "Chery", 46),
        BrandEntity("brand_exeed", "Exeed", 47),
        BrandEntity("brand_by_d", "BYD", 48),
        BrandEntity("brand_gac", "GAC", 49),
        BrandEntity("brand_changan", "Changan", 50),
        BrandEntity("brand_baic", "BAIC", 51),
        BrandEntity("brand_jetour", "Jetour", 52),
        BrandEntity("brand_tank", "Tank", 53),
        BrandEntity("brand_polestar", "Polestar", 54),
        BrandEntity("brand_lucid", "Lucid", 55),
        BrandEntity("brand_rox", "ROX", 56),
        BrandEntity("brand_daihatsu", "Daihatsu", 57),
        BrandEntity("brand_subaru", "Subaru", 58),
        BrandEntity("brand_ssangyong", "SsangYong", 59),
        BrandEntity("brand_proton", "Proton", 60),
        BrandEntity("brand_other", "Other", 60)


    )
}
