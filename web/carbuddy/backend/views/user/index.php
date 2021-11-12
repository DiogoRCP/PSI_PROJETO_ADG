<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel backend\models\UserSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Users';

?>
<div class="users-index">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [



            'username',
            'usertype',
            'nif',
            'birsthday',
            'email:email',
            'phonenumber',
            'registrationdate',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
