//  音符の点滅
function noteFlashing() {
  $('#note').fadeToggle(1000, "linear", function() {
    noteFlashing();
  });
}

//  前回の持ち越し項目の読込と表示
function getAttempted() {
  var attemptedLists = localStorage.getItem("todo");
  if (attemptedLists == null) return;
  var attArray = attemptedLists.split(',');
  for (var i = 0; i < attArray.length; i++) {
    $('#attempted-lists').append("<div class=attempted-list id=attempted-list><span id=delete>×</span>&emsp;<a>" + attArray[i] + "</a></div>");
  }
}

$(function() {
  getAttempted();
  noteFlashing();

  //  「しなければ！」ボタンを押したとき
  $('input[name="todo-button"]').click(function() {
    var todo = $('input[name="todo"]').val();
    //  空なら再入力を促す
    if (todo == "") {
      alert("何か入力して");
      return;
    }
    //  入力項目を!todo以下に表示
    $('#todo-lists').append("<div class=todo-list id=todo-list><span id=delete>×</span>&emsp;<a>" + todo + "</a></div>");
  });

  //  各項目の×を押したとき
  $('#todo-lists').on("click", "#delete", function() {
    $(this).parent().remove();
  });

  $('#carry-out-lists').on("click", "#delete", function() {
    $(this).parent().remove();
  });

  $('#attempted-lists').on("click", "#delete", function() {
    $(this).parent().remove();
  });

  //  「保存・更新」ボタンを押したとき
  $('input[name="save"]').click(function() {
    //  各ステータス（todo, 次回,　前回）ごとに表示されている項目数を取得
    var todoNum = $('#todo-lists > div > a').length;
    var carryNum = $('#carry-out-lists > div > a').length;
    var attemptedNum = $('#attempted-lists > div > a').length;
    //  全ステータスで項目がなければ、キャッシュをクリアしリターン
    if (todoNum + carryNum + attemptedNum == 0) {
        alert("nothing content");
        localStorage.removeItem("todo");
        return;
    }
    //  全ステータスの項目数が5以上ならばリターン
    if (todoNum + carryNum + attemptedNum > 5) {
      alert("over!");
      return;
    }

    //  ローカルストレージ保存用の配列
    var attemptedLists = [];

    //  前回の項目を保存
    if(attemptedNum != 0) {
      for (var i = 0; i < attemptedNum; i++) {
        if (i == 0) {
          attemptedLists[i] = $('#attempted-lists > div').first().next().find('a').text();
        } else if (i == 1) {
          attemptedLists[i] = $('#attempted-lists > div').first().next().next().find('a').text();
        } else if (i == 2) {
          attemptedLists[i] = $('#attempted-lists > div').first().next().next().next().find('a').text();
        } else if (i == 3) {
          attemptedLists[i] = $('#attempted-lists > div').first().next().next().next().next().find('a').text();
        } else if (i == 4) {
          attemptedLists[i] = $('#attempted-lists > div').first().next().next().next().next().next().find('a').text();
        }
      }
    }

    //  次回の項目を保存
    if(carryNum != 0) {
      for (var i = attemptedNum; i < attemptedNum + carryNum; i++) {
        if (i == attemptedNum) {
          attemptedLists[i] = $('#carry-out-lists > div').first().next().find('a').text();
        } else if (i == attemptedNum + 1) {
          attemptedLists[i] = $('#carry-out-lists > div').first().next().next().find('a').text();
        } else if (i == attemptedNum + 2) {
          attemptedLists[i] = $('#carry-out-lists > div').first().next().next().next().find('a').text();
        } else if (i == attemptedNum + 3) {
          attemptedLists[i] = $('#carry-out-lists > div').first().next().next().next().next().find('a').text();
        } else if (i == attemptedNum + 4) {
          attemptedLists[i] = $('#carry-out-lists > div').first().next().next().next().next().next().find('a').text();
        }
      }
    }

    //  todo!から次回の項目へコピーするメソッド
    function addCarryOutLists() {
      $('#carry-out-lists').append("<div class=carry-out-list id=carry-out-list><span id=delete>×</span>&emsp;<a>" + attemptedLists[i] + "</a></div>");
    }

    //  todo!の項目を保存し、次回の項目へ移動後、todoの項目から削除
    for (var i = attemptedNum + carryNum; i < attemptedNum + carryNum + todoNum; i++) {
      if (i == attemptedNum + carryNum) {
        attemptedLists[i] = $('#todo-lists > div').first().next().find('a').text();
        if (attemptedLists[i] != "") addCarryOutLists();
        $('#todo-lists > div').first().next().remove();
      } else if (i == attemptedNum + carryNum + 1) {
        attemptedLists[i] = $('#todo-lists > div').first().next().find('a').text();
        if (attemptedLists[i] != "") addCarryOutLists();
        $('#todo-lists > div').first().next().remove();
      } else if (i == attemptedNum + carryNum  + 2) {
        attemptedLists[i] = $('#todo-lists > div').first().next().find('a').text();
        if (attemptedLists[i] != "") addCarryOutLists();
        $('#todo-lists > div').first().next().remove();
      } else if (i == attemptedNum + carryNum + 3) {
        attemptedLists[i] = $('#todo-lists > div').first().next().find('a').text();
        if (attemptedLists[i] != "") addCarryOutLists();
        $('#todo-lists > div').first().next().remove();
      } else if (i == attemptedNum + carryNum + 4) {
        attemptedLists[i] = $('#todo-lists > div').first().next().find('a').text();
        if (attemptedLists[i] != "") addCarryOutLists();
        $('#todo-lists > div').first().next().remove();
      }
    }
    //  ローカルストレージのクリア
    localStorage.removeItem("todo");
    //  ローカルストレージが空ならばストレージにキャッシュする
    if(localStorage.getItem("todo") == null) localStorage.setItem("todo", attemptedLists);
    alert("保存・更新しました");
  });
})
