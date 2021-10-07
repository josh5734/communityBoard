$('#add_category').submit(function(event) {
    var form = $(this);
    $.ajax({
        type : form.attr('method'),
        url : form.attr('action'),
        data : form.serialize()
    }).done(function(c) {
        $("#category").append("<option value=" + c.id + ">" + c.name + "</option>");
        $("#category").val(c.id);

        alert(c.name + " 카테고리가 추가되었습니다.");
    }).fail(function() {
        alert('error');
    });
    event.preventDefault();
});