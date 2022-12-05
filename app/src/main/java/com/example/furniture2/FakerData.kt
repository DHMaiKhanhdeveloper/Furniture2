package com.example.furniture2

internal val categories by lazy {
    listOf(
        Category(
            R.drawable.living_room, "Living room", mutableListOf(
                Furniture(
                    R.drawable.table1, "Table",
                    listOf(
                        Detail(R.drawable.table1, "Billiards Table", R.raw.table1),
                        Detail(R.drawable.table2, "Square Table", R.raw.table2),
                        Detail(R.drawable.table3, "Reinforced Table", R.raw.table3),
                        Detail(R.drawable.table4, "Simple Table", R.raw.table4),
                        Detail(R.drawable.table5, "Outdoor Wooden Table", R.raw.table5),
                        Detail(R.drawable.table6, "Office Table", R.raw.table6),
                        Detail(R.drawable.table7, "Night Table", R.raw.table7)
                    )
                ),
                Furniture(
                    R.drawable.chair1, "Chair",
                    listOf(
                        Detail(R.drawable.chair1, "Modern Club Chair", R.raw.chair1),
                        Detail(R.drawable.chair2, "Rocking Chair", R.raw.chair2),
                        Detail(R.drawable.chair3, "Outdoor Chair", R.raw.chair3),
                        Detail(R.drawable.chair4, "Plastic Chair", R.raw.chair4),
                        Detail(R.drawable.chair5, "Yellow Chair", R.raw.chair5),
                        Detail(R.drawable.chair6, "Wooden Chair", R.raw.chair6)
                    )
                ),
                Furniture(
                    R.drawable.tivi1, "Tivi",
                    listOf(
                        Detail(R.drawable.tivi1, "Red Flat Screen TV", R.raw.tivi1),
                        Detail(R.drawable.tivi2, "Black Flat Screen TV", R.raw.tivi2),
                        Detail(R.drawable.tivi3, "TV Stand", R.raw.tivi3),
                        Detail(R.drawable.tivi4, "White Flat Screen TV", R.raw.tivi4)
                    )
                ),
                Furniture(
                    R.drawable.clock1, "Clock",
                    listOf(
                        Detail(R.drawable.clock1, "Wall clock", R.raw.clock1),
                        Detail(R.drawable.clock2, "Steampunk Pool Clock", R.raw.clock2),
                        Detail(R.drawable.clock3, "clock Tower", R.raw.clock3),
                        Detail(R.drawable.clock4, "Office Clock", R.raw.clock4),
                        Detail(R.drawable.clock5, "Wall clock", R.raw.clock5)
                    )
                ),
                Furniture(
                    R.drawable.closet, "Closet",
                    listOf(
                        Detail(R.drawable.closet, "Closet", R.raw.funiture1)
                    )
                ),
                Furniture(
                    R.drawable.imac, "Laptop",
                    listOf(
                        Detail(R.drawable.laptop, "Laptop", R.raw.laptop1),
                        Detail(R.drawable.imac, "Imac", R.raw.imac)

                    )
                ),
                Furniture(
                    R.drawable.car1, "Car",
                    listOf(
                        Detail(R.drawable.car1, "Travaler Orange", R.raw.car2),
                        Detail(R.drawable.car2, "Shift Tuner Red", R.raw.car2),
                        Detail(R.drawable.car3, "Horizon baja car silver", R.raw.car3),
                        Detail(R.drawable.car4, "Travaler ", R.raw.car4)
                    )
                )

            )
        ),

        Category(
            R.drawable.bathroom, "Bathroom", listOf(
                Furniture(
                    R.drawable.bath2, "Comb",
                    listOf(

                        Detail(R.drawable.bath2, "Comb Pink", R.raw.bath2),
                        Detail(R.drawable.bath3, "Comb Black", R.raw.bath3)
                    )
                ),
                Furniture(
                    R.drawable.bath1, "Sink",
                    listOf(
                        Detail(R.drawable.bath1, "Concrete Sink", R.raw.bath1),
                        Detail(R.drawable.sinknew, "Blue Sink", R.raw.sinknew),
                        Detail(R.drawable.sink1, "Table Sink", R.raw.sink1)
                    )
                ),
                Furniture(
                    R.drawable.bath4, "Brush",
                    listOf(
                        Detail(R.drawable.bath4, "Brush1", R.raw.bath4),
                        Detail(R.drawable.brush2, "Brush2", R.raw.brush2)
                    )
                ),
                Furniture(
                    R.drawable.bath5, "Hair Dryer",
                    listOf(
                        Detail(R.drawable.bath5, "Pink Hair Dryer", R.raw.bath5),
                        Detail(R.drawable.hair_dryer, "Blue Hair Dryer", R.raw.hairdryer)
                    )
                ),
                Furniture(
                    R.drawable.bath6, "Shower Gel",
                    listOf(
                        Detail(R.drawable.bath6, "Shower Gel", R.raw.bath6),
                        Detail(R.drawable.shower, "Pink Shower Gel", R.raw.shower)
                    )
                ),
                Furniture(
                    R.drawable.guong, "Mirror",
                    listOf(
                        Detail(R.drawable.bath7, "Mirror Yellow", R.raw.bath7),
                        Detail(R.drawable.bath8, "Mirror Large", R.raw.bath8),
                        Detail(R.drawable.bath9, "Mirror Black", R.raw.bath9)
                    )
                ),
                Furniture(
                    R.drawable.toilet, "Toilet",
                    listOf(
                        Detail(R.drawable.toilet, "Toilet", R.raw.toilet)
                    )
                )


            )
        ),
        Category(
            R.drawable.bedroom, "Bedroom", listOf(

                Furniture(
                    R.drawable.dresser2, "Dresser",
                    listOf(
                        Detail(R.drawable.dresser1, "Wooden Dresser", R.raw.desk1),
                        Detail(R.drawable.dresser2, "Iron Dresser", R.raw.desk2)
                    )
                ),
                Furniture(
                    R.drawable.desk1, "Desk",
                    listOf(
                        Detail(R.drawable.desk1, "Iron Table", R.raw.desk1),
                        Detail(R.drawable.desk2, "Wooden Table", R.raw.desk2)
                    )
                ),
                Furniture(
                    R.drawable.bed1, "Bed",
                    listOf(
                        Detail(R.drawable.bed1, "Wooden Bed", R.raw.bed1),
                        Detail(R.drawable.bed2, "Wrought Iron Bed", R.raw.bed2),
                        Detail(R.drawable.bed3, "Modern Sleigh Bed", R.raw.bed3),
                        Detail(R.drawable.bed4, "Wrought Iron Bed", R.raw.bed4)
                    )
                ),
                Furniture(
                    R.drawable.clock1, "Clock",
                    listOf(
                        Detail(R.drawable.clock1, "Wall clock", R.raw.clock1),
                        Detail(R.drawable.clock2, "Steampunk Pool Clock", R.raw.clock2),
                        Detail(R.drawable.clock3, "clock Tower", R.raw.clock3),
                        Detail(R.drawable.clock4, "Office Clock", R.raw.clock4),
                        Detail(R.drawable.clock5, "Wall clock", R.raw.clock5)
                    )
                ),
                Furniture(
                    R.drawable.lamp1, "Lamp",
                    listOf(
                        Detail(R.drawable.lamp1, "Desk Lamp", R.raw.lamp1),
                        Detail(R.drawable.lamp2, "Street Lamp", R.raw.lamp2),
                        Detail(R.drawable.lamp3, "Desk Lamp", R.raw.lamp3),
                        Detail(R.drawable.lamp4, "Standing Lamp", R.raw.lamp4),
                        Detail(R.drawable.lamp5, "Lamp", R.raw.lamp5)
                    )
                )
            )

        ),
        Category(
            R.drawable.kitchen, "Kitchen", listOf(
                Furniture(
                    R.drawable.kitchen5, "Cup",
                    listOf(
                        Detail(R.drawable.kitchen5, "Cup of Coffee", R.raw.kitchen5),
                        Detail(R.drawable.cup1, "Round Cup", R.raw.cup1),
                        Detail(R.drawable.cup4, "Cup of Coffee", R.raw.cup4)
                    )
                ),
                Furniture(
                    R.drawable.kitchen2, "Frying Fan",
                    listOf(

                        Detail(R.drawable.kitchen2, "Frying Fan", R.raw.kitchen2),
                        Detail(R.drawable.pan2, "Iron Frying Fan", R.raw.pan)
                    )
                ),
                Furniture(
                    R.drawable.kitchen, "Other Furniture ",
                    listOf(
                        Detail(R.drawable.kitchen1, "Microwave", R.raw.kitchen1),
                        Detail(R.drawable.kitchen2, "Frying Fan", R.raw.kitchen2),
                        Detail(R.drawable.kitchen3, "Refrigerator", R.raw.kitchen3),
                        Detail(R.drawable.kitchen4, "Chef Knife", R.raw.kitchen4),
                        Detail(R.drawable.kitchen5, "Cup of Coffee", R.raw.kitchen5),
                        Detail(R.drawable.kitchen6, "Forks and Spoons", R.raw.kitchen6),
                        Detail(R.drawable.kitchen7, "Egg Beater", R.raw.kitchen7),
                        Detail(R.drawable.kitchen8, "Washing Machine", R.raw.kitchen8)

                    )
                )

            )
        )
    )
}