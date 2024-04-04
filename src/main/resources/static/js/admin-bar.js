
$(document).ready(function (){

    // ======================================== top bar =========================================================
    $("#myInfo").click(function(){
        if(!$("#myInfo").hasClass("active")){ // active없는 경우
            $("#myInfo").addClass("active");
            $("#infoDropdown").show();
            console.log("active 활성화");
        }
        else if($("#myInfo").hasClass("active")){ // active 있는 경우
            $("#myInfo").removeClass("active");
            $("#infoDropdown").hide();
            console.log("active 비활성화");
        }
    });


    // 다른곳을 눌렀을 때 #infoDropdown 닫기 - 근데 광역 적용되어서 물어보기
    $(document).mouseup(function (e){
        let target = $("#infoDropdown");

        /* if (!target.is(e.target) && target.has(e.target).length === 0){
             //target.css("display","none");
             console.log("광역");
             $("#infoDropdown").hide();
             $("#myInfo").removeClass("active");
         }*/

        /*if($(e.target).closest('#infoDropdown').length == 0) { // 레이어 밖 클릭

        //if(target.has(e.target).length==0) {
            console.log(target.has(e.target).length)
            $("#myInfo").removeClass("active");
            target.hide();
            console.log("active 비활성화");
        }*/
    }); // mouseup end


    // ======================================== nav bar =========================================================
    $("#purchaseBtn").on("click",function(){
        if(!$("#purchaseBtn").hasClass("active")){ // active없는 경우
            $("#purchaseBtn").addClass("active");
            $("#purchaseUtilities").show();
            console.log("active 활성화");

        }
        else if($("#purchaseBtn").hasClass("active")){ // active 있는 경우
            $("#purchaseBtn").removeClass("active");
            $("#purchaseUtilities").hide();
            console.log("active 비활성화");
        }
    });

    $("#accountBtn").on("click",function(){

        console.log("sessionId>>>> " + $("#sessionId").val());

        if(!$("#accountBtn").hasClass("active")){ // active없는 경우
            $("#accountBtn").addClass("active");
            $("#accountUtilities").show();
            console.log("active 활성화");
        }
        else if($("#accountBtn").hasClass("active")){ // active 있는 경우
            $("#accountBtn").removeClass("active");
            $("#accountUtilities").hide();
            console.log("active 비활성화");
        }



    });
}); //ready end

function select(num){
    if( num == 1 ){
        $("#dropdownMenuButton").text("접수중");
    }
    else if(num == 2){
        $("#dropdownMenuButton").text("계약 완료");
    }
    else if(num == 3){
        $("#dropdownMenuButton").text("계약 종료");
    }
    else if(num == 4){
        $("#dropdownMenuButton").text("계약 취소");
    }
}