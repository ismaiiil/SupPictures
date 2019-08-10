$(function(){

    $("#dropdownCategoryMenu a").click(function(){

        $(".btn-category").text($(this).text());
        $(".btn-category").val($(this).text());
        console.log("wazaa");
    });

});