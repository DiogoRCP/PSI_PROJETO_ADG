<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\CarSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */
$this->title = 'Vehicles';
?>
<div class="cars-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Vehicles', ['create'], ['class' => 'btn btn-success']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            //'id',
            'vin',
            'brand',
            'model',
            'color',
            'carType',
            'displacement',
            'fuelType',
            //'registration',
            'modelyear',
            'kilometers',
            'state',
            //'userId',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
