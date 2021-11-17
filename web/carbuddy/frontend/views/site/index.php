<?php

/* @var $this yii\web\View */

use yii\grid\GridView;
/* @var $searchModel frontend\models\CarSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */
$this->title = 'CarBuddy';
?>
<div class="site-index">

    <div class="jumbotron text-center bg-transparent">
        <img src="../images/logo_white.png" width="250">
    </div>

    <div class="index-content">
        <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'filterModel' => $searchModel,
            'columns' => [
                //'id',
                'registration',
                'vin',
                'brand',
                'model',
                //'color',
                //'carType',
                'displacement',
                'fuelType',
                'modelyear',
                'kilometers',
                'state',
                //'userId',

               // ['class' => 'yii\grid\ActionColumn'],
            ],
        ]); ?>
    </div>
</div>
