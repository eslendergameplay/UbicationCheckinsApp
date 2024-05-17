package com.example.CheckinsApp.Foursqaure

class FoursquareApiRV {

    var meta:Meta? = null
    var response:FoursquareResponse? = null

}

class Meta{
    var code:Int = -1
    var errorDetail:String = ""
}

class FoursquareResponse{
    var venues:ArrayList<Venue> ?= null
}

class Venue{
    var id:String = ""
    var name:String = ""
    var location:Location? = null
    var categories:ArrayList<Category>? = null
    var imagePreview:String? = null
    var iconPreview:String? = null
    var estadisticas:ArrayList<VenueStat>? = null
}

class Location{
    var lat:Double = 0.0
    var lng:Double = 0.0
    var city:String = ""
    var state:String = ""
    var country:String = ""
}

class Category{
    var id:String = ""
    var name:String = ""
    var icon:Icon? = null
    var pluralName:String = ""
    var shortName:String = ""
    var iconPreview:String? = null
}

open class Icon{
    var prefix:String = ""
    var suffix:String = ""

}

class VenuesStats{
    var meta:Meta?= null
    var response:ResponseStats?= null
}

class ResponseStats{
    var venue:VenueStat? = null
}

class VenueStat{
    var stats:Stats? = null
    var likes:Like? =null
    var photos:PhotosCount? = null
    var beenHere:Users? = null
    var listed:Checkins? = null

}


class Checkins {
    var count:Int = -1

}

class Stats{
    var tipCount:Int = -1
}

class Like{
    var count:Int = -1
}

class PhotosCount{
    var count:Int = -1
    var groups:ArrayList<ItemFoto>? = null
}

class ItemFoto{
    var items:ArrayList<Fotos>? = null
}

class Fotos{
    var prefix:String = ""
    var suffix:String = ""
    var width:Int = -1
    var height:Int = -1

}

class Users{
    var count:Int = -1
}

class Tips{
    var count:Int = -1
}

class FoursqaureApiResponseUser{
    var meta:Meta? = null
    var response:ResponseUser? = null
}

class ResponseUser {
    var user:User? = null
}

class User{
    var id:String = ""
    var firstName:String = ""
    var lastName:String = ""
    var photo:Fotos? = null
    var friends:Friends? = null
    var tips:Tips? = null
    var photos:PhotosCount2? = null
    var checkins:Checkins? = null
    var fotopreview:String = ""
}

class PhotosCount2{
    var count:Int = -1
}

class Friends{
    var count:Int = 0
}

class Likes{
    var meta:Meta?= null
}

class CheckinsUser{
    var meta:Meta? = null
}

class FourquareApiCategories{
    var meta:Meta? = null
    var response:CategoriesResponse? = null

}

class CategoriesResponse{
    var categories:ArrayList<Category>? = null
}

class FoursquareApiVenuesPorCategoria{
    var meta:Meta? = null
    var response:FoursquareCateResponse? = null
}

class FoursquareCateResponse{
    var venues:ArrayList<Venue>? = null
}

class FoursquareApiLikesPorCategoria{
    var meta:Meta? = null
    var response:FoursquareLikesResponse? = null

}

class FoursquareLikesResponse{
    var venues:ItemVenueLike? = null
}

class ItemVenueLike{
    var items:ArrayList<Venue>? = null
}
