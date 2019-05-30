$("#btnSalvar").on("click", function(){

    $.ajax({
        type: "POST",
        url: "../../SrvProprietario?type=insert",
        dataType:'json',
        data: {
        	"PROPRIETARIO":JSON.stringify({
	        	"CD_PROPRIETARIO": "0",
		    	"NM_PROPRIETARIO":"Maki Nishikino",
		    	"TEL_PROPRIETARIO": "332815454",
		    	"DS_ENDERECO": "Av. maritaca 123",
		    	"NR_CPF": "11861000"
        	})
        },
        success: function(response){
            console.log("Entered", response);
        },
        error:function(error){
            console.log("error",error);
        },

    });
});

$("#btnVeiculo").on("click", function(){
	$("#btnVeiculo>div").toggleClass("hidden");
});

$("#btnEstadia").on("click", function(){
	$("#btnEstadia>div").toggleClass("hidden");
});

$("#btnEstadia").on("mouseleave", function(){
	$("#btnEstadia>div").addClass("hidden");
});

$("#btnVeiculo").on("mouseleave", function(){
	$("#btnVeiculo>div").addClass("hidden");
});



