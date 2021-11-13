<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\CarSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */
$this->title = 'Vehicles';
?>
<div class="cars-index">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Vehicle', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

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

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
