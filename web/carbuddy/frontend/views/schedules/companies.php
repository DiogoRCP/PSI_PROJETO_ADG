<?php
/* @var $model frontend\models\Companies */

use yii\helpers\Html;
use yii\helpers\Url;
use yii\helpers\VarDumper;

$this->title = 'Companies';

?>
<div class="cars-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <div class="body-content">
        <div class="row">
            <?php foreach ($model as $company) { ?>
                <div class="col-md-3"
                     style="width: 10rem; height: 10rem; display: flex; align-items: center; justify-content: center">
                    <a href="<?= Url::to(['schedules/create', 'company' => $company->id]) ?>">
                        <h2><?= $company->companyname ?></h2>
                    </a>
                </div>
            <?php } ?>
        </div>
    </div>
</div>