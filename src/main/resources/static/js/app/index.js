var main = {
    init: function () {
        var _this = this;//객체의 메소드안에서 사용된 this는 해당 메소드를 호출한 객체에 바인딩된다. (즉 main을 호출한 객체가됨)
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        })

        $('#btn-delete').on('click', function(){
            _this.delete();
        })
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json', //서버측에서 전송받은 데이터 타입
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data) //직렬화해서 보냄
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; //js에서 url로 페이지 이동방법(cf. 페이지 이름으로 이동)
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function() {
        var id=$('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            contentType: 'application/json; charset=utf-8',
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};
main.init();