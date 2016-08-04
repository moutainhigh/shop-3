$(".submit-input input").keyup(function () {
    console.log('123')
    if ($(this).val().length == 6) {
        if (!$('.submit-btn').hasClass('active'))
            $('.submit-btn').addClass('active');
            $('.submit-btn img').attr('src', "imgs/submit-w.png");
    } else {
        if ($('.submit-btn').hasClass('active'))
            $('.submit-btn').removeClass('active');
            $('.submit-btn img').attr('src', "imgs/submit-r.png");
    }
    
})