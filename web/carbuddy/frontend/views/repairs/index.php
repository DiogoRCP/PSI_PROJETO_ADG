<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\RepairsSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Repairs';

//$this->params['breadcrumbs'][] = $this->title;
?>
<div class="repairs-index">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>

    <?php if (Yii::$app->user->can('frontendCRUDRepair')) { ?>
        <p>
            <?= Html::a('Create Repairs', ['create'], ['class' => 'btn btn-secondary']) ?>
        </p>

    <?php } ?>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>


    <?php if (\Yii::$app->request->get('car') != null) { ?>
        <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'filterModel' => $searchModel,
            'columns' => [
                'kilometers',
                'repairdate',
                'repairdescription',
                'state',
                'repairtype',
            ],
        ]); ?>
    <?php } else { ?>
        <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'filterModel' => $searchModel,
            'columns' => [
                //['class' => 'yii\grid\SerialColumn'],

                //'id',
                'kilometers',
                'repairdate',
                'repairdescription',
                'state',
                'repairtype',
                'carId',
                //'contributorId',

                ['class' => 'yii\grid\ActionColumn'],
            ],
        ]);
    } ?>

</div>
